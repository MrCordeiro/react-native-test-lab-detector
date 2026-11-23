#!/usr/bin/env bash
# Compiles the pure Kotlin logic and runs its JUnit tests.
# Needs Java 11 or later, curl, and unzip. Needs no Gradle and no Android SDK.
# Downloads pinned tools into .tools/ and checks their SHA-256.
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
TOOLS="$ROOT/.tools"
PKG="java/com/pluckd/testlabdetector"
KOTLIN_VERSION="2.1.0"

sha256() {
  if command -v sha256sum >/dev/null; then sha256sum "$1"; else shasum -a 256 "$1"; fi | cut -d' ' -f1
}

# download <url> <file> <sha256>
download() {
  local file="$TOOLS/$2"
  if [ ! -f "$file" ]; then
    echo "Downloading $2"
    curl -sSLf -o "$file.part" "$1"
    mv "$file.part" "$file"
  fi
  if [ "$(sha256 "$file")" != "$3" ]; then
    echo "Checksum mismatch for $2. Delete $file and try again." >&2
    exit 1
  fi
}

mkdir -p "$TOOLS"
download "https://github.com/JetBrains/kotlin/releases/download/v$KOTLIN_VERSION/kotlin-compiler-$KOTLIN_VERSION.zip" \
  "kotlin-compiler-$KOTLIN_VERSION.zip" b6698d5728ad8f9edcdd01617d638073191d8a03139cc538a391b4e3759ad297
download "https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar" \
  "junit-4.13.2.jar" 8e495b634469d64fb8acfa3495a065cbacc8a0fff55ce1e31007be4c16dc57d3
download "https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar" \
  "hamcrest-core-1.3.jar" 66fdef91e9739348df7a096aa384a5685f4e875584cce89386a7a47251c4d8e9

KOTLINC="$TOOLS/kotlinc-$KOTLIN_VERSION"
if [ ! -d "$KOTLINC" ]; then
  unzip -q "$TOOLS/kotlin-compiler-$KOTLIN_VERSION.zip" -d "$TOOLS/unzip"
  mv "$TOOLS/unzip/kotlinc" "$KOTLINC"
  rm -rf "$TOOLS/unzip"
fi

CP="$TOOLS/junit-4.13.2.jar:$TOOLS/hamcrest-core-1.3.jar"
OUT="$TOOLS/out"
rm -rf "$OUT"

# Only TestLabSetting.kt. The module file needs Android and Expo classes.
"$KOTLINC/bin/kotlinc" -nowarn -cp "$CP" -d "$OUT" \
  "$ROOT/android/src/main/$PKG/TestLabSetting.kt" \
  "$ROOT"/android/src/test/$PKG/*.kt

java -cp "$OUT:$CP:$KOTLINC/lib/kotlin-stdlib.jar" org.junit.runner.JUnitCore \
  com.pluckd.testlabdetector.TestLabSettingTest

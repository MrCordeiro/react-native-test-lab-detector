// The package has no iOS native code. Turn off iOS autolinking.
module.exports = {
  dependency: {
    platforms: {
      ios: null,
    },
  },
};

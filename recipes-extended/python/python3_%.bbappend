PACKAGECONFIG:class-target += " tk readline"
# The Python recipe is currently broken with a dependency loop when we include Tkinter in the build with a global PACKAGECONFIG
# Luckily we can build the native Python 3 (required by xorgproto) without it using :class-target
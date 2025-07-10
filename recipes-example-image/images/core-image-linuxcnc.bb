SUMMARY = "A very basic Wayland image with a terminal and Chromium"

IMAGE_FEATURES += "splash package-management ssh-server-dropbear hwcodecs weston"

REQUIRED_DISTRO_FEATURES = "x11 opengl"

LICENSE = "MIT"

inherit core-image

CORE_IMAGE_BASE_INSTALL += "weston-xwayland matchbox-terminal pcmanfm nano linuxcnc linuxcnc-doc"

QB_MEM = "-m 2048"

#IMAGE_FSTYPES = "wic
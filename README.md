This README file contains information on the contents of the meta-linuxcnc layer.

Please see the corresponding sections below for details.


Patches
=======

Please submit any patches against the meta-linuxcnc layer as a pull request on GitHub

Maintainer: Peter Den Hartog <pddenhar@gmail.com>

Table of Contents
=================

  I. Adding the meta-linuxcnc layer to your build
 II. Building and Running
 III. Known Issues


I. Adding the meta-linuxcnc layer to your build
=================================================

```
git clone git@github.com:pddenhar/meta-linuxcnc.git
bitbake-layers add-layer meta-linuxcnc
```

II. Building and Running
========================
The following is included in this layer's `layer.conf` so that you can build LinuxCNC (`bitbake linuxcnc`):

```
ERROR_QA:remove = "patch-status"

HOSTTOOLS += "ps kill whoami pidof ipcs fuser mandb "
```

There is an example image recipe that creates a Wayland based Poky image with LinuxCNC installed. You can build it with
`bitbake core-image-linuxcnc`. 

The following is also included in meta-linuxcnc's `layer.conf` so that the image can build:

```
# This is needed because LinuxCNC will crash trying to use the BusyBox version of PS
PACKAGECONFIG:pn-bind = "libedit"
PREFERRED_PROVIDER_base-utils = "packagegroup-core-base-utils"
VIRTUAL-RUNTIME_base-utils = "packagegroup-core-base-utils"
VIRTUAL-RUNTIME_base-utils-hwclock = "util-linux-hwclock"
VIRTUAL-RUNTIME_base-utils-syslog = "rsyslog"
```

To build the image and run it in qemu (with KVM accceleration and OpenGL support), use the following command:

```
bitbake core-image-linuxcnc && runqemu core-image-linuxcnc kvm gl sdl
```

III. Known Issues
=================
LinuxCNC currently fails to launch *unless* you run it from a terminal (i.e. not using the launcher button in Weston). Why?

Currently, axis seems to be the only UI that launches successfully. It's likely the other UIs have dependencies that were missed in `RDEPENDS`. Please submit a pull request if you find any.
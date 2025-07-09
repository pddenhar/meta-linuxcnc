# The Python recipe is currently broken with a dependency loop when we include Tkinter in the build with a global PACKAGECONFIG
# Luckily we can build the native Python 3 (required by xorgproto) without it using :class-target
PACKAGECONFIG:class-target += " tk readline"

PACKAGECONFIG[tk] = ",,tk8"

# Switch tk and tcl versions from 9 to 8
RDEPENDS:${PN}-tkinter:remove = 'tk'
RDEPENDS:${PN}-tkinter:remove = 'tk-lib'
RDEPENDS:${PN}-idle:remove = 'tcl'
RDEPENDS:${PN}-tkinter += "${@bb.utils.contains('PACKAGECONFIG', 'tk', '${MLPREFIX}tk8 ${MLPREFIX}tk8-lib', '', d)}"
RDEPENDS:${PN}-idle += "${@bb.utils.contains('PACKAGECONFIG', 'tk', '${PN}-tkinter ${MLPREFIX}tcl8', '', d)}"

SUMMARY = "Yet Another Python Parser System"
HOMEPAGE = "https://github.com/mk-fg/yapps"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=75db4afbd75feb0b856fb4fade51aad2"

SRC_URI = "git://github.com/smurfix/yapps.git;branch=master;protocol=https"

# Modify these as desired
PV = "2.2.0+git"
SRCREV = "67541062093846bb53f011da0f4d489d63375d2d"

S = "${WORKDIR}/git"

SYSROOT_DIRS += "${localstatedir}"

inherit setuptools3_legacy

RDEPENDS:${PN} += "python3-core python3-doctest"

BBCLASSEXTEND = "native"
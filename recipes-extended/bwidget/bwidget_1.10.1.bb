LICENSE = "TCL"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=af21afb4e406f3d8e15b91dd3fa0a978"

SRC_URI = "https://master.dl.sourceforge.net/project/tcllib/BWidget/${PV}/bwidget-${PV}.tar.gz"
SRC_URI[sha256sum] = "4aea02f38cf92fa4aa44732d4ed98648df839e6537d6f0417c3fe18e1a34f880"

# There is definitely some way to use tclConfig.sh or similar to figure out where it install this stupid package
# but I am not wasting my time on that right now
# DEPENDS += "tcl"

FILES:${PN}:append = " ${libdir}/tcl8.6/* "	

do_install () {
	install -m 0755 -d "${D}${libdir}/tcl8.6/bwidget${PV}"
	cp "${S}"/*.tcl "${D}${libdir}/tcl8.6/bwidget${PV}/"
	cp -r "${S}/images" "${D}${libdir}/tcl8.6/bwidget${PV}/"
	cp -r "${S}/lang" "${D}${libdir}/tcl8.6/bwidget${PV}/"
	chmod -R 755 "${D}${libdir}/tcl8.6/bwidget${PV}"
}


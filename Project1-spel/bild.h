#pragma once
#ifndef BILD_E
#define BILD_E
#include <Windows.h>
class bild {
public:
	bild();
	void		set_h(HDC);			// kopplar en HDC till klassen
	void		set_b(HBITMAP);		// kopplar en HBITMAP till klassen
	HDC			get_h();			// hämtar hdc:n
	HBITMAP		get_b();			// hämtar hbitmap:n
	void		delete_hb(HWND);	// tar bort klass-variabel
private:
	HDC HB;
	HBITMAP BB;
};


#endif
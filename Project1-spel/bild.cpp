#include "bild.h"
#include <Windows.h>
bild::bild()
{
	HB = NULL;
	BB = NULL;
}
void bild::set_b(HBITMAP b)
{
	BB = b;
}
void bild::set_h(HDC a) {
	HB = a;
}

HDC bild::get_h()
{
	return HB;
}
HBITMAP bild::get_b()
{
	return BB;
}

void bild::delete_hb(HWND hw)
{
	DeleteObject(BB);
	ReleaseDC(hw, HB);
	DeleteDC(HB);
}

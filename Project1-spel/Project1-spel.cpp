
#include <iostream>
#define WIN32_LEAN_AND_MEAN	
#include <Windows.h>
#include <cstdlib>
#include <string>
#include <vector>
#include <sstream>
#include <time.h>
#include "bild.h"
#include "spelare.h"
#include "curtain.h"
#include <conio.h>
#include <stdio.h>

// variabler ----------------------------------------------------------------
double		CPUFreq = 0.0;
bool		running = true;
int			innerWidth, innerHeight;	// höjd/bredd på programmets fönster
rido		curt;						// programminfo
spelare		player;						// klassvariabel för spelaren
bool		tester = false;
// vector -------------------------------------------------------------------
std::vector<bild>	frames;		// vector som lagrar bilder som används i spelet
// Device Contexts ----------------------------------------------------------
HDC			hDC;				// DC till spelet
HDC			buffer;				// buffer
// BITMAPS ------------------------------------------------------------------
HBITMAP		oldBitmap[9];		// Lagrar orginalbilderna
HBITMAP		bufferIMG;			// bitmap till buffern
// funktioner ---------------------------------------------------------------
LRESULT		CALLBACK	winProc(HWND, UINT, WPARAM, LPARAM);
ATOM 		doRegister(HINSTANCE);
BOOL 		initInstance(HINSTANCE, int);
int			initalizeAll(HWND);
void		releaseAll(HWND);
void		update();			// Alla uppdateringar
void		render();			// Ritar ut bilden i fönstret
bool		framerate(int);		// Uppdateringsfrekvensen
double		getFreq();
void		movement();			// spelarens rörelse (animering)
void		detect_input();		// kolla efter användarinput
void		print_ui();			// skriver ut användargränssnittet

// main --------------------------------------------------------------------
inline __int64 performanceCounter() noexcept {
	LARGE_INTEGER li;
	::QueryPerformanceCounter(&li);
	return li.QuadPart;
};
//---------------------------------------------------------------------------
int WINAPI WinMain(_In_ HINSTANCE hi, _In_opt_ HINSTANCE hp, _In_ LPSTR lp, _In_ int n) {
	UNREFERENCED_PARAMETER(hp);
	UNREFERENCED_PARAMETER(lp);
	if (!(doRegister(hi)) || !(initInstance(hi, n))) {
		MessageBox(NULL, (LPCSTR)"Fel, Kan ej registrera eller skapa fönstret i windows", (LPCSTR)"ERROR", MB_ICONERROR | MB_OK);
		return 0;
	}
	MSG msg;
	HWND ini = FindWindowA("spel", "spel");
	initalizeAll(ini);
	// Ny loop där vi själv bestämmer när saker ritas ut
	while (running == true) {
		if (PeekMessage(&msg, NULL, 0, 0, PM_REMOVE) > 0) {
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
		if (framerate(30) == true) {
			update();
			render();
		}
	}
	return 0;
}
//---------------------------------------------------------------------------
bool framerate(int timeStamp) {
	static __int64 last = performanceCounter();
	if (((double)((performanceCounter() - last)) / CPUFreq) > timeStamp) {
		last = performanceCounter();
		return true;
	}
	return false;
}
//---------------------------------------------------------------------------
LRESULT CALLBACK winProc(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam) {
	switch (Msg) {
	case WM_COMMAND:
		switch (wParam) {
			case(4):
				;
			break;
		}
	case WM_MOUSEMOVE:
		if (curt.get_scen() == 0 || curt.get_scen() == 2 || curt.get_scen() == 3) {
			curt.set_m(HIWORD(lParam), 'y');
			if (curt.get_scen() == 3) {
				curt.set_m(LOWORD(lParam), 'x');
			}
		}
		break;
	case WM_LBUTTONDOWN:
		if (curt.get_scen() == 0 || curt.get_scen() == 2) {
			curt.set_m(LOWORD(lParam), HIWORD(lParam));
		}
		else if (curt.get_scen() == 1) {
			curt.set_m(LOWORD(lParam), HIWORD(lParam));
		}
		break;
	case WM_KEYDOWN:
		if (curt.get_scen() == 1) {
			break;
		}
	case WM_LBUTTONUP:
		if (curt.get_active() == 102) {
			running = false;
			releaseAll(hWnd);
			PostQuitMessage(0);
		}
		break;
	case WM_CLOSE:
		PostQuitMessage(0);
	case WM_DESTROY:
		running = false;
		releaseAll(hWnd);
	default:
		return DefWindowProc(hWnd, Msg, wParam, lParam);
	}
	return 0;
}
//---------------------------------------------------------------------------
void update() {
	static int counter = 0;
	counter++;
	// meny
	int mY = curt.get_m('y'), mX = curt.get_m('x');
	if (curt.get_scen() == 0) {
		// menyalternativ
		if ((mY > 300 && mY < 360)) {
			curt.set_active(0);
		}
		else if ((mY > 380 && mY < 440)) {
			curt.set_active(1);
		}
		else if ((mY > 440 && mY < 500)) {
			curt.set_active(2);
		}
		else if ((mY > 500 && mY < 640)) {
			curt.set_active(3);
		}
		// val
			//spel
		if ((mX > (innerWidth / 2) - 100 && mX < (innerWidth / 2)) && ((mY > 300 && mY < 380))) {
			curt.set_active(100);
			curt.set_scen(3);
			curt.set_m(0, 0);
		}
			// exit
		else if ((mX > (innerWidth / 2) - 100 && mX < (innerWidth / 2)) && ((mY > 500 && mY < 640))) {
			curt.set_active(102);

		}
	}
	// spel
	else if (curt.get_scen() == 1) {
		// spelarens rörelse
		detect_input();
	}
	// pause
	else if (curt.get_scen() == 2) {
		if ((mY > 300 && mY < 360)) {
			curt.set_active(0);
		}
		else if ((mY > 380 && mY < 440)) {
			curt.set_active(1);
		}
		else if ((mY > 440 && mY < 500)) {
			curt.set_active(2);
		}
		// val
			// resume
		if ((mX > (innerWidth / 2) - 100 && mX < (innerWidth / 2)) && ((mY > 300 && mY < 380))) {
			curt.set_active(100);
			curt.set_scen(1);
			curt.set_m(0, 0);
		}
			// back to meny
		else if ((mX > ((innerWidth / 2) - 100) && mX < (innerWidth / 2)) && ((mY > 380 && mY < 440))) {
			curt.set_active(101);
			curt.set_scen(0);
			curt.set_m(0, 0);
		}
			// exit
		else if ((mX > ((innerWidth / 2) - 100) && mX < (innerWidth / 2)) && ((mY > 440 && mY < 500))) {
			curt.set_active(102);
		}
	}
	// chr. create
	else if (curt.get_scen() == 3) {
		bool check = (mY > 200 && mY < 300);
		// warrior
		if (check && (mX > 340 && mX < 430)) {
			curt.set_active(0);
		}
		// mage
		else if (check && (mX > 430 && mX < 535)) {
			curt.set_active(1);
		}
		// tank
		else if (check && (mX > 535 && mX < 635)) {
			curt.set_active(2);
		}
		// random
		else if (check && (mX > 635 && mX < 780)) {
			curt.set_active(3);
		}
		// val
			//spel
		if ((mX > (innerWidth / 2) - 100 && mX < (innerWidth / 2)) && ((mY > 300 && mY < 380))) {
			
		}
		// exit
		else if ((mX > (innerWidth / 2) - 100 && mX < (innerWidth / 2)) && ((mY > 500 && mY < 640))) {

		}
	}
}
//---------------------------------------------------------------------------
void render() {
	// meny
	if (curt.get_scen() == 0) {
		BitBlt(buffer, 0, 0, innerWidth, innerHeight, frames[1].get_h(), 0, 0, SRCCOPY);

		std::string text[4] = { "NEW GAME", "CONTINUE", "SHOW HIGHSCORE" , "EXIT" };

		for (int i = 0; i < 4; i++) {
			if (curt.get_active() == i) {
				SetTextColor(buffer, COLORREF(RGB(200, 0, 0)));
			}
			else {
				SetTextColor(buffer, COLORREF(RGB(0, 0, 0)));
			}
			SetBkColor(buffer, RGB(255, 255, 255));
			TextOut(buffer, (innerWidth / 2) - 80, (50 * i) + 350, text[i].c_str(), text[i].size());
		}
	}
	// spel
	else if (curt.get_scen() == 1) {
		// bakgrund
		if (player.get_location() == 0) {
			BitBlt(buffer, 0, 0, innerWidth, innerHeight, frames[3].get_h(), 0, 0, SRCCOPY);
			movement();
		}
		// spelare
			TransparentBlt(buffer, 420, 250, 150, 150, frames[0].get_h(), 0, 0, 500, 500, COLORREF(RGB(255, 0, 255)));

		// pil
			if (player.get_click() == true) {
				TransparentBlt(buffer, curt.get_m('x') - 25, curt.get_m('y') - 36, 50, 50, frames[2].get_h(), 0, 0, 500, 500, COLORREF(RGB(255, 0, 255)));
			}
		// UI
			print_ui();
			
	}
	// pause
	else if (curt.get_scen() == 2) {
		BitBlt(buffer, 250, 0, innerWidth, innerHeight, frames[5].get_h(), 0, 0, SRCCOPY);
		std::string text[3] = { "RESUME", "BACK TO MENU", "EXIT" };

		for (int i = 0; i < 3; i++) {
			if (curt.get_active() == i) {
				SetTextColor(buffer, COLORREF(RGB(200, 0, 0)));
			}
			else {
				SetTextColor(buffer, COLORREF(RGB(0, 0, 0)));
			}
			SetBkColor(buffer, RGB(255, 255, 255));
			TextOut(buffer, (innerWidth / 2) - 80, (50 * i) + 350, text[i].c_str(), text[i].size());
		}
	}
	// chr. create
	else if (curt.get_scen() == 3) {
		if (tester == false) {
			SetBkColor(buffer, RGB(255, 255, 255));
			BitBlt(buffer, 0, 0, innerWidth, innerHeight, frames[1].get_h(), 0, 0, SRCCOPY);
			HWND parent = FindWindow("spel", "spel");
			HWND ny = CreateWindowA("EDIT", "...", WS_CHILD | WS_VISIBLE, 400, 400, 50, 20, parent, NULL, NULL, NULL);
			BringWindowToTop(ny);
			ShowWindow(ny, 1);
			UpdateWindow(ny);
			tester = true;
		}
		// klasser
			std::string text[4] = { "WARRIOR", "MAGE", "TANK" , "RANDOM" };
			TransparentBlt(buffer, (innerWidth / 2) - 150, 200, 300, 100, frames[8].get_h(), 0, 0, 300, 100, COLORREF(RGB(255, 0, 255)));
			for (int i = 0; i < 4; i++) {
				if (curt.get_active() == i) {
					SetTextColor(buffer, COLORREF(RGB(200, 0, 0)));
				}
				else {
					SetTextColor(buffer, COLORREF(RGB(0, 0, 0)));
				}
				SetBkColor(buffer, RGB(255, 255, 255));
				TextOut(buffer, (innerWidth / 2 - 140) + (110 * i), 180, text[i].c_str(), text[i].size());
			}
		// namn

	}
	// buffer
	BitBlt(hDC, 0, 0, innerWidth, innerHeight, buffer, 0, 0, SRCCOPY);
}
//---------------------------------------------------------------------------
int	initalizeAll(HWND hWnd) {
	// Hämta fönstrets riktiga bredd & höjd
	RECT		windowRect;
	GetClientRect(hWnd, &windowRect);
	innerWidth = windowRect.right;
	innerHeight = windowRect.bottom;
	srand(time(0));

	hDC = GetDC(hWnd);					// Koppla fönstret till en DC
	buffer = CreateCompatibleDC(hDC);	// skapar en hdc för buffern
	const int len = 9;					// antal bilder
	int i = 0;
	bild a[len];

	for (i = 0; i < len; i++) {
		a[i].set_h(CreateCompatibleDC(hDC));
	}
	// Läs in bilderna
	a[0].set_b((HBITMAP)LoadImage(NULL, (LPCTSTR)("bilder/test.bmp"), IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE));		// [0] spelare
	a[1].set_b((HBITMAP)LoadImage(NULL, (LPCTSTR)("bilder/meny.bmp"), IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE));		// [1] meny-bg
	a[2].set_b((HBITMAP)LoadImage(NULL, (LPCTSTR)("bilder/pil.bmp"), IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE));			// [2] spel-pil
	a[3].set_b((HBITMAP)LoadImage(NULL, (LPCTSTR)("bilder/background.bmp"), IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE));	// [3] spel-bg
	a[4].set_b((HBITMAP)LoadImage(NULL, (LPCTSTR)("bilder/grass.bmp"), IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE));		// [4] spel-bg: gräs
	a[5].set_b((HBITMAP)LoadImage(NULL, (LPCTSTR)("bilder/pause.bmp"), IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE));		// [5] pause-bg
	a[6].set_b((HBITMAP)LoadImage(NULL, (LPCTSTR)("bilder/inv_1.bmp"), IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE));		// [6] spel-inv: botten
	a[7].set_b((HBITMAP)LoadImage(NULL, (LPCTSTR)("bilder/ability.bmp"), IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE));		// [7] spel-inv: förmågor (400 x 200)
	a[8].set_b((HBITMAP)LoadImage(NULL, (LPCTSTR)("bilder/classsymbols.bmp"), IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE));// [8] klasser
	bufferIMG = CreateCompatibleBitmap(hDC, innerWidth, innerHeight);
	SelectObject(buffer, bufferIMG);
	// Läs in bilderna till varsin HDC
	for(i = 0;i<len;i++) {
		frames.push_back(a[i]);

		oldBitmap[i] = (HBITMAP)SelectObject(a[i].get_h(), a[i].get_b());
	}
	// Ger CPU-frekvensen som används med performanceCounter();
	CPUFreq = getFreq();
	return 1;
}
//---------------------------------------------------------------------------
void releaseAll(HWND hWnd) {
	// tar bort bitmaps och hdc:s
	int tmp = frames.size();
	int i;
	for (i = 0; i < tmp; i++) {
		frames[i].get_h();
		SelectObject(frames[i].get_h(), oldBitmap[i]);

		frames[i].delete_hb(hWnd);
		DeleteObject(oldBitmap[i]);

	}

	frames.clear();

	ReleaseDC(hWnd, hDC);
	DeleteDC(hDC);
	running = false;
}
//---------------------------------------------------------------------------
BOOL initInstance(HINSTANCE hInstance, int nCmdShow) {
	//Bredd och höjd för fönstret som vi skapar
	int app_Wid = 1000;
	int app_Hei = 800;
	LPCTSTR CName = "spel";

	HWND hWnd = CreateWindowEx(
		0, CName, (LPCSTR)CName, WS_OVERLAPPEDWINDOW | WS_VISIBLE | WS_BORDER,
		((GetSystemMetrics(SM_CXSCREEN) - app_Wid) >> 1),  //Sätter fönstret i mitten i x-led;
		((GetSystemMetrics(SM_CYSCREEN) - app_Hei) >> 1),  //Sätter fönstret i mitten i Y-led;
		app_Wid, app_Hei, NULL, NULL, hInstance, NULL);

	if (!hWnd) {
		return FALSE;
	}

	ShowWindow(hWnd, nCmdShow);
	UpdateWindow(hWnd);
	return TRUE;
}
//---------------------------------------------------------------------------
ATOM doRegister(HINSTANCE hi) {
	WNDCLASSEX wincl;
	LPCTSTR	ClsName = "spel";

	wincl.hInstance = hi;
	wincl.lpszClassName = ClsName;
	wincl.lpfnWndProc = winProc;
	wincl.style = CS_DBLCLKS | CS_HREDRAW | CS_VREDRAW;
	wincl.cbSize = sizeof(WNDCLASSEX);
	wincl.hIcon = LoadIcon(NULL, IDI_APPLICATION);
	wincl.hIconSm = LoadIcon(NULL, IDI_APPLICATION);
	wincl.hCursor = LoadCursor(NULL, IDC_ARROW);
	wincl.lpszMenuName = NULL;
	wincl.cbClsExtra = 0;
	wincl.cbWndExtra = 0;
	wincl.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);

	return RegisterClassEx(&wincl);
}
//---------------------------------------------------------------------------
double getFreq() {
	LARGE_INTEGER li;
	QueryPerformanceFrequency(&li);
	return double(li.QuadPart) / 1000.0;
}
//---------------------------------------------------------------------------
void movement() {
	// rörelse
	if (player.get_click() == true) {
		int x_led = 500, y_led = 400;
		int spd = player.get_stats('s');
		// x-led
		if (x_led < curt.get_m('x')) {
			player.move_x(spd * -1);
		}
		else if (x_led > curt.get_m('x')) {
			player.move_x(spd);
		}
		// y-led
		if (y_led < curt.get_m('y')) {
			player.move_y(spd * -1);
		}
		else if (y_led > curt.get_m('y')) {
			player.move_y(spd);
		}
	}
	// reset bg-position
	if (player.get_x() < -2000) {
		player.set_x(-500);
		player.set_y(-400);
	}
	else if (player.get_x() > 2000) {
		player.set_x(-500);
		player.set_y(-400);
	}
	if (player.get_y() < -1600) {
		player.set_x(-500);
		player.set_y(-400);
	}
	else if (player.get_y() > 1600) {
		player.set_x(-500);
		player.set_y(-400);
	}
	// första bilden
	TransparentBlt(buffer, player.get_x(), player.get_y(), 2000, 1600, frames[4].get_h(), 0, 0, 2000, 1600, COLORREF(RGB(255, 0, 255)));
	// andra bilden
		// höger
	if(player.get_x() < -1000){
		int nx = (player.get_x() + 1000);
		TransparentBlt(buffer, innerWidth + nx, player.get_y(), (nx * -1), 1600, frames[4].get_h(), 0, 0, (nx * -1), 1600, COLORREF(RGB(255, 0, 255)));
	}
		// vänster
	else if (player.get_x() > 0) {
		int nx = (player.get_x());
		TransparentBlt(buffer, 0, player.get_y(), nx, 1600, frames[4].get_h(), 2000 - nx, 0, nx, 1600, COLORREF(RGB(255, 0, 255)));
	}
	// tredje bilden
		// ner
	if (player.get_y() < -800) {
		int ny = (player.get_y() + 800);
		TransparentBlt(buffer, player.get_x(), innerHeight + ny, 2000, (ny * -1), frames[4].get_h(), 0, 0, 2000, (ny * -1), COLORREF(RGB(255, 0, 255)));
	}
		// upp
	else if (player.get_y() > 0) {
		int ny = (player.get_y());
		TransparentBlt(buffer, player.get_x(), 0, 2000, ny, frames[4].get_h(), 0, 1600 - ny, 2000, ny, COLORREF(RGB(255, 0, 255)));
	}
	// fjärde bilden 
		// höger + ner
	if (player.get_x() < -1000 && player.get_y() < -800) {
		int nx = (player.get_x() + 1000);
		int ny = (player.get_y() + 800);
		TransparentBlt(buffer, innerWidth + nx, innerHeight + ny, (nx * -1), (ny * -1), frames[4].get_h(), 0, 0, (nx * -1), (ny * -1), COLORREF(RGB(255, 0, 255)));
	}
		// höger + upp
	else if (player.get_x() < -1000 && player.get_y() > 0){
		int nx = (player.get_x() + 1000);
		int ny = (player.get_y());
		TransparentBlt(buffer, innerWidth + nx, 0, (nx * -1), ny, frames[4].get_h(), 0, 1600 - ny, (nx * -1), ny, COLORREF(RGB(255, 0, 255)));
	}
	// vänster + ner
	else if (player.get_x() < -1000 && player.get_y() < -800) {
		int nx = (player.get_x());
		int ny = (player.get_y() + 800);
		TransparentBlt(buffer, 0, innerHeight + ny, nx, (ny * -1), frames[4].get_h(), 2000 - nx, 0, nx, (ny * -1), COLORREF(RGB(255, 0, 255)));
	}
	// vänster + upp
	else if (player.get_x() < -1000 && player.get_y() > 0) {
		int nx = (player.get_x());
		int ny = (player.get_y());
		TransparentBlt(buffer, 0, 0, nx, ny, frames[4].get_h(), 2000 - nx, 1600 - ny, nx, ny, COLORREF(RGB(255, 0, 255)));
	}
}
//---------------------------------------------------------------------------
void detect_input()
{
	if (GetAsyncKeyState(VK_LBUTTON) < 0) {
		player.set_click(true);

	}
	if (GetAsyncKeyState(VK_RBUTTON) < 0) {
		player.set_click(false);
	}
	if (GetAsyncKeyState(VK_ESCAPE) < 0) {
		curt.set_scen(2);
	}
}
//---------------------------------------------------------------------------
void print_ui()
{
	std::ostringstream aa;
	// inventory: botten
		BitBlt(buffer, 0, 600, 1000, 200, frames[6].get_h(), 0, 0, SRCCOPY);
	// liv
		for (int i = 0; i < player.get_a_life(); i++) {
			aa << "|";
		}
		std::string text = aa.str();
		SetBkColor(buffer, RGB(252, 107, 3));
		TextOut(buffer, 100, 680, text.c_str(), text.size());
		aa.str("");
		aa.clear();
	// liv 2
		aa << "liv: ";
		text.clear();
		text = aa.str();
		SetBkColor(buffer, RGB(255, 255, 255));
		TextOut(buffer, 60, 680, text.c_str(), text.size());
	// xp
		aa.str("");
		aa.clear();
		aa << "Level: " << player.level_data(0) << " | XP: " << player.level_data(1) << "/" << player.get_xpcap();
		text.clear();
		text = aa.str();
		SetBkColor(buffer, RGB(255, 255, 255));
		TextOut(buffer, 60, 660, text.c_str(), text.size());
	// chr. info
		aa.str("");
		aa.clear();
		aa << "* " << player.get_name() << " *";
		text.clear();
		text = aa.str();
		SetBkColor(buffer, RGB(252, 186, 3));
		TextOut(buffer, 70, 620, text.c_str(), text.size());
	// stats
		aa.str("");
		aa.clear();
		text.clear();
		text = player.print_stats();

		SetBkColor(buffer, RGB(255, 255, 255));
		TextOut(buffer, 30, 640, text.c_str(), text.size());
	// abilities
		std::vector<int> ab = player.get_abil();
		TransparentBlt(buffer, 400, 640, 200, 100, frames[7].get_h(), 0, 0, 400, 200, COLORREF(RGB(255, 255, 255)));
}
//---------------------------------------------------------------------------


/*
att göra:

*/
#pragma once
#ifndef CLICKER_E
#define CLICKER_E
#include <Windows.h>
class clicker {
public:
	clicker();
	int get_cx();
	int get_cy();

	void set_cx(int);
	void set_cy(int);

	void click_reset();
private:
	int x, y;
};


#endif
#pragma once

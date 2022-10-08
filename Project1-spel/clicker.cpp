#include "clicker.h"

clicker::clicker()
{
	x = 500;
	y = 400;
}

int clicker::get_cx()
{
	return x;
}

int clicker::get_cy()
{
	return y;
}

void clicker::set_cx(int a)
{
	x = a;
}

void clicker::set_cy(int a)
{
	y = a;
}

void clicker::click_reset()
{
	x = 500;
	y = 400;
}

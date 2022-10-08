#pragma once
#ifndef CURTAIN_E
#define CURTAIN_E
#include "clicker.h"
class rido {
public:
	rido();
	void set_scen(int);
	void set_active(int);
	void set_m(int, char);
	void set_m(int, int);

	int get_scen();
	int get_active();
	int get_m(char);
private:
	int scen, active;
	clicker pilar;
};


#endif
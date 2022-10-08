#pragma once
#ifndef ABILITY_E
#define ABILITY_E
#include "stats.h"
#include "spelare.h"
class ability {
public:
	ability();
	ability(int);
	int get_a();
	int get_id();
	int get_a_stat(int);

private:
	int id;
};


#endif
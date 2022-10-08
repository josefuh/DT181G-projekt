#pragma once
#ifndef STATS_E
#define STATS_E
#include <Windows.h>
class stats {
public:
	stats();

	void statup(char);
	int getstat(char);
	int get_level();
	int get_xp();
	void add_xp(int);
	int get_cap();
	void set_stat(int, char);
private:
	int attack, speed, magic, health, defence;
	int level, xp;
};


#endif
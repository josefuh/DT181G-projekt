#pragma once
#ifndef SPELARE_E
#define SPELARE_E
#include <Windows.h>
#include "stats.h"
#include "abilities.h"
#include <string>
#include <vector>
class spelare {
public:
	spelare();
	void set_x(int);		// anger ny x-pos
	void set_y(int);		// anger ny y-pos
	void move_x(int);		// flyttar fram i x-ledet
	void move_y(int);		// flyttar fram i y-ledet
	void new_locale(int);	// flyttar spelarens plats 

	int get_x();			// hämtar x-värde
	int get_y();			// hämtar y-värde
	int get_a_life();		// hämtar spelarens liv
	int get_dir();			// hämtar spelarens riktning
	bool get_click();		// hämtar 'clicked'-värde
	int get_location();		// berättar vart spelaren är
	std::string get_name();	// returnerar spelarens namn
	int level_data(int);	// returnerar [0] = level, [1] = xp
	int get_stats(char);	// returnerar en stat
	int get_xpcap();		// returnerar xp:n som behövs för att levla upp
	std::vector<int> get_abil();// returnerar id:t av alla sparade förmågor

	void add_abil();		// lägger till en förmåga
	void hits();			// minskar spelarens liv
	void set_dir(int);		// ny riktning
	void set_click(bool);	// click ->true/false
	void reset_spelare();	// nollställer värden	
	void stats_add(char);	// ökar en stat
	void lvlup(int);		// ökar xp
	std::string print_stats();// skriver ut alla stats
	void write_name(std::string);
private:
	int x, y;				// spelfigurens x/y-pos
	int liv;				// spelarens liv
	int lv, Xp;				// spelarens level & xp
	int direction;			// spelfigurens rörelseriktning
	int location;			// i OW eller i dungeon
	bool clicked;			// klickat
	stats stat_c;			// spelarens stats
	std::vector<int> abil;	// spelarens förmågor
	std::string name;		// spelarens namn
};


#endif
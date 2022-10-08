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

	int get_x();			// h�mtar x-v�rde
	int get_y();			// h�mtar y-v�rde
	int get_a_life();		// h�mtar spelarens liv
	int get_dir();			// h�mtar spelarens riktning
	bool get_click();		// h�mtar 'clicked'-v�rde
	int get_location();		// ber�ttar vart spelaren �r
	std::string get_name();	// returnerar spelarens namn
	int level_data(int);	// returnerar [0] = level, [1] = xp
	int get_stats(char);	// returnerar en stat
	int get_xpcap();		// returnerar xp:n som beh�vs f�r att levla upp
	std::vector<int> get_abil();// returnerar id:t av alla sparade f�rm�gor

	void add_abil();		// l�gger till en f�rm�ga
	void hits();			// minskar spelarens liv
	void set_dir(int);		// ny riktning
	void set_click(bool);	// click ->true/false
	void reset_spelare();	// nollst�ller v�rden	
	void stats_add(char);	// �kar en stat
	void lvlup(int);		// �kar xp
	std::string print_stats();// skriver ut alla stats
	void write_name(std::string);
private:
	int x, y;				// spelfigurens x/y-pos
	int liv;				// spelarens liv
	int lv, Xp;				// spelarens level & xp
	int direction;			// spelfigurens r�relseriktning
	int location;			// i OW eller i dungeon
	bool clicked;			// klickat
	stats stat_c;			// spelarens stats
	std::vector<int> abil;	// spelarens f�rm�gor
	std::string name;		// spelarens namn
};


#endif
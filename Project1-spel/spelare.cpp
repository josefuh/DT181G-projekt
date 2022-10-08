#include "spelare.h"
#include <sstream>
//---------------------------------------------------------------------------
spelare::spelare() {
	x = -500;
	y = -400;
	location = 0;
	liv = stat_c.getstat('h');
	direction = 0;
	clicked = false;
	name = "yo";
	abil.push_back(1);
}
//---------------------------------------------------------------------------
void spelare::set_x(int a)
{
		x = a;
	
}
//---------------------------------------------------------------------------
void spelare::set_y(int a)
{
		y = a;
	
}
//---------------------------------------------------------------------------
void spelare::move_x(int a)
{
	x = x + a;
}
//---------------------------------------------------------------------------
void spelare::move_y(int a)
{

	y = y + a;
}
//---------------------------------------------------------------------------
void spelare::new_locale(int a)
{
	location = a;
}
//---------------------------------------------------------------------------
int spelare::get_x()
{
	return x;
}
//---------------------------------------------------------------------------
int spelare::get_y()
{
	return y;
}
//---------------------------------------------------------------------------
void spelare::add_abil()
{
	ability p1;

	abil.push_back(p1.get_a());
}
//---------------------------------------------------------------------------
void spelare::hits()
{
	liv = liv - 1;
}
//---------------------------------------------------------------------------
void spelare::set_dir(int a)
{
	direction = a;
}
//---------------------------------------------------------------------------
void spelare::set_click(bool a)
{
	if (a == true) {
		clicked = true;
	}
	else {
		clicked = false;
	}
}
//---------------------------------------------------------------------------
void spelare::reset_spelare()
{
	x = 0;
	y = 0;
	liv = stat_c.getstat('h');
	direction = 0;
	clicked = false;
	lv = stat_c.get_level();
	Xp = stat_c.get_xp();
}
//---------------------------------------------------------------------------
void spelare::stats_add(char st)
{
	stat_c.statup(st);
}
//---------------------------------------------------------------------------
void spelare::lvlup(int a)
{
	stat_c.add_xp(a);
}
//---------------------------------------------------------------------------
std::string spelare::print_stats()
{
	std::ostringstream au;
	au << "att: " << stat_c.getstat('a') << " mag: " << stat_c.getstat('m') << " def: " << stat_c.getstat('d') << "spd: " << stat_c.getstat('s') << " hp: " << stat_c.getstat('h');
	return au.str();
}
//---------------------------------------------------------------------------
void spelare::write_name(std::string a)
{
	name = name + a;
}
//---------------------------------------------------------------------------
int spelare::get_a_life()
{
	return liv;
}
//---------------------------------------------------------------------------
int spelare::get_dir()
{
	return direction;
}
//---------------------------------------------------------------------------
bool spelare::get_click() {
	return clicked;
}
//---------------------------------------------------------------------------
int spelare::get_location()
{
	return location;
}
//---------------------------------------------------------------------------
std::string spelare::get_name()
{
	return name;
}
//---------------------------------------------------------------------------
int spelare::level_data(int a)
{
	if (a == 0) {
		return stat_c.get_level();
	}
	else {
		return stat_c.get_xp();
	}
}
//---------------------------------------------------------------------------
int spelare::get_stats(char st)
{
	return stat_c.getstat(st);
}
//---------------------------------------------------------------------------
int spelare::get_xpcap()
{
	return stat_c.get_cap();
}
//---------------------------------------------------------------------------
std::vector<int> spelare::get_abil()
{
	return abil;
}
//---------------------------------------------------------------------------

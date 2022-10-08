#include "stats.h"
//---------------------------------------------------------------------------
int calc_xp(int);
//---------------------------------------------------------------------------
stats::stats()
{
	attack = 1;
	health = 20;
	defence = 1;
	magic = 1;
	speed = 1;
	level = 1;
	xp = 0;
}
//---------------------------------------------------------------------------
void stats::statup(char st)
{
	switch (st) {
	case('a'):
		attack++;
		break;
	case('s'):
		speed++;
		break;
	case('d'):
		defence++;
		break;
	case('m'):
		magic++;
	case('h'):
		health = health + 5;
		break;
	default:
		break;
	}
}
//---------------------------------------------------------------------------
int stats::getstat(char st)
{
	switch (st) {
	case('a'):
		return attack;
	case('s'):
		return speed;
	case('d'):
		return defence;
	case('m'):
		return magic;
	case('h'):
		return health;
	default:
		return 0;
	}
}
//---------------------------------------------------------------------------
int stats::get_level()
{
	return level;
}
//---------------------------------------------------------------------------
int stats::get_xp()
{
	return xp;
}
//---------------------------------------------------------------------------
void stats::add_xp(int a)
{
	int cap = calc_xp(level);
	xp = xp + a;
	if (xp >= cap) {
		level++;
		xp = xp - cap;
	}
}
//---------------------------------------------------------------------------
int stats::get_cap()
{
	return calc_xp(level);
}
//---------------------------------------------------------------------------
void stats::set_stat(int a, char st)
{
	switch (st) {
	case('a'):
		attack = a;
		break;
	case('s'):
		speed = a;
		break;
	case('d'):
		defence = a;
		break;
	case('m'):
		magic = a;
		break;
	case('h'):
		health = a;
		break;
	default:
		break;
	}
}
//---------------------------------------------------------------------------
int calc_xp(int lvl) {
	return (lvl * (12 * lvl));
}
//---------------------------------------------------------------------------
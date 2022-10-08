#pragma once
// ska köra main() funktionen

#ifdef TF_PLATFORM_WINDOWS

extern Tuff::App* Tuff::AppCreate();

int main(int argc, char** argv)
{
	auto app = Tuff::AppCreate();
	app->run();
	delete app;
}
#endif
#pragma once
#ifdef TF_PLATFORM_WINDOWS
	#ifdef TF_BUILD_DLL
		#define TUFF_API __declspec(dllexport)
	#else
#define TUFF_API __declspec(dllimport)
	#endif
#else
	#error WINDOWS ONLY
#endif

#define BIT(x) (1 << x)
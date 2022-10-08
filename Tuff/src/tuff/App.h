#pragma once

#include "core.h"

namespace Tuff {
	class TUFF_API App
	{
	public:
		App();
		virtual ~App();
		void run();
	};
	// funktionen ska skrivas i klienten
	App* AppCreate();
}


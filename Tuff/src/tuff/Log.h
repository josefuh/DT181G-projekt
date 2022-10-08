#pragma once
#include "core.h"


namespace Tuff 
{
	class Log
	{
	public:
		static void Init();

		inline static std::shared_ptr<spdlog::logger>& GetCoreLogger() { return s_CoreLogger; }
		inline static std::shared_ptr<spdlog::logger>& GetClientLogger() { return s_ClientLogger; }
	private:
		static std::shared_ptr<spdlog::logger> s_CoreLogger;
		static std::shared_ptr<spdlog::logger> s_ClientLogger;
	};
}

// gör en log med: TF_CORE_*level*(*text*)
#define TF_CORE_TRACE(...)    ::Tuff::Log::GetCoreLogger()->trace(__VA_ARGS__)
#define TF_CORE_INFO(...)     ::Tuff::Log::GetCoreLogger()->info(__VA_ARGS__)
#define TF_CORE_WARN(...)     ::Tuff::Log::GetCoreLogger()->warn(__VA_ARGS__)
#define TF_CORE_ERROR(...)    ::Tuff::Log::GetCoreLogger()->error(__VA_ARGS__)
#define TF_CORE_CRITICAL(...) ::Tuff::Log::GetCoreLogger()->critical(__VA_ARGS__)

// Client log macros
#define TF_TRACE(...)         ::Tuff::Log::GetClientLogger()->trace(__VA_ARGS__)
#define TF_INFO(...)          ::Tuff::Log::GetClientLogger()->info(__VA_ARGS__)
#define TF_WARN(...)          ::Tuff::Log::GetClientLogger()->warn(__VA_ARGS__)
#define TF_ERROR(...)         ::Tuff::Log::GetClientLogger()->error(__VA_ARGS__)
#define TF_CRITICAL(...)      ::Tuff::Log::GetClientLogger()->critical(__VA_ARGS__)
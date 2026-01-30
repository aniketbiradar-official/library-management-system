/* =========================================================
   THEME.JS
   Day / Night (Light / Dark) Theme Toggle
   ---------------------------------------------------------
   - Default: Dark Mode
   - Persistent using localStorage
   - Icon swap (Moon ↔ Sun)
   - Zero backend dependency
   ========================================================= */

(function () {
    const THEME_KEY = "library-theme";
    const DARK = "dark";
    const LIGHT = "light";

    // Cache DOM once
    const root = document.documentElement;
    const toggleBtn = document.getElementById("themeToggle");

    // If toggle button does not exist (e.g., login page)
    if (!toggleBtn) return;

    const icon = toggleBtn.querySelector("i");

    /* -----------------------------------------------------
       Apply theme to document
    ----------------------------------------------------- */
    function applyTheme(theme) {
        if (theme === LIGHT) {
            root.setAttribute("data-theme", "light");
            icon.classList.remove("fa-moon");
            icon.classList.add("fa-sun");
        } else {
            root.removeAttribute("data-theme");
            icon.classList.remove("fa-sun");
            icon.classList.add("fa-moon");
        }
    }

    /* -----------------------------------------------------
       Load saved theme or system preference
    ----------------------------------------------------- */
    function loadTheme() {
        const savedTheme = localStorage.getItem(THEME_KEY);

        if (savedTheme === LIGHT || savedTheme === DARK) {
            applyTheme(savedTheme);
        } else {
            // Optional: Respect OS preference on first visit
            const prefersLight = window.matchMedia("(prefers-color-scheme: light)").matches;
            applyTheme(prefersLight ? LIGHT : DARK);
        }
    }

    /* -----------------------------------------------------
       Toggle theme handler
    ----------------------------------------------------- */
    function toggleTheme() {
        const isLight = root.getAttribute("data-theme") === LIGHT;
        const newTheme = isLight ? DARK : LIGHT;

        applyTheme(newTheme);
        localStorage.setItem(THEME_KEY, newTheme);

        // Subtle click feedback
        toggleBtn.classList.add("theme-click");
        setTimeout(() => toggleBtn.classList.remove("theme-click"), 150);
    }

    /* -----------------------------------------------------
       Init
    ----------------------------------------------------- */
    loadTheme();
    toggleBtn.addEventListener("click", toggleTheme);
})();

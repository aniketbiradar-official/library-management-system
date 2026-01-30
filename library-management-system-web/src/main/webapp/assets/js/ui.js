/* =========================================================
   UI.JS
   Subtle Interactions & Visual Polish
   ---------------------------------------------------------
   - Smooth dropdown behavior
   - Search focus glow handling
   - Card & table hover polish
   - Safe across all pages
   ========================================================= */

(function () {
    "use strict";

    /* -----------------------------------------------------
       DROPDOWN (Reports Menu)
    ----------------------------------------------------- */
    document.querySelectorAll(".dropdown").forEach(dropdown => {
        let timeout;

        dropdown.addEventListener("mouseenter", () => {
            clearTimeout(timeout);
            const menu = dropdown.querySelector(".dropdown-menu");
            if (menu) {
                menu.style.display = "block";
                menu.style.opacity = "0";
                menu.style.transform = "translateY(-4px)";

                requestAnimationFrame(() => {
                    menu.style.transition = "opacity 0.2s ease, transform 0.2s ease";
                    menu.style.opacity = "1";
                    menu.style.transform = "translateY(0)";
                });
            }
        });

        dropdown.addEventListener("mouseleave", () => {
            const menu = dropdown.querySelector(".dropdown-menu");
            if (menu) {
                timeout = setTimeout(() => {
                    menu.style.opacity = "0";
                    menu.style.transform = "translateY(-4px)";
                    setTimeout(() => {
                        menu.style.display = "none";
                    }, 200);
                }, 120);
            }
        });
    });

    /* -----------------------------------------------------
       SEARCH BAR FOCUS HINT
    ----------------------------------------------------- */
    document.querySelectorAll(".search-bar input").forEach(input => {
        input.addEventListener("focus", () => {
            input.parentElement.classList.add("search-active");
        });

        input.addEventListener("blur", () => {
            input.parentElement.classList.remove("search-active");
        });
    });

    /* -----------------------------------------------------
       TABLE ROW HOVER POLISH
    ----------------------------------------------------- */
    document.querySelectorAll("table tbody tr").forEach(row => {
        row.addEventListener("mouseenter", () => {
            row.style.transition = "background 0.15s ease";
            row.style.background = "rgba(99, 102, 241, 0.06)";
        });

        row.addEventListener("mouseleave", () => {
            row.style.background = "";
        });
    });

    /* -----------------------------------------------------
       BUTTON CLICK FEEDBACK
    ----------------------------------------------------- */
    document.querySelectorAll("button, .primary-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            btn.classList.add("btn-click");
            setTimeout(() => btn.classList.remove("btn-click"), 140);
        });
    });

    /* -----------------------------------------------------
       SAFE FADE-IN FOR MAIN CONTENT
    ----------------------------------------------------- */
    const container = document.querySelector(".app-container");
    if (container) {
        container.style.opacity = "0";
        container.style.transform = "translateY(6px)";

        window.addEventListener("load", () => {
            container.style.transition = "opacity 0.4s ease, transform 0.4s ease";
            container.style.opacity = "1";
            container.style.transform = "translateY(0)";
        });
    }

})();

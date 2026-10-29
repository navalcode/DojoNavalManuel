const summary = document.querySelector("[data-error-summary]");
if (summary) summary.focus();
const filters = document.querySelector("[data-responsive-filter]");
if (filters) {
  const narrow = window.matchMedia("(max-width: 580px)");
  const adapt = () => {
    if (!narrow.matches || !filters.contains(document.activeElement)) {
      filters.open = !narrow.matches;
    }
  };
  adapt();
  narrow.addEventListener("change", adapt);
}

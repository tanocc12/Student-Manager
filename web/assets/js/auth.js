(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll("[data-password-toggle]").forEach(function (btn) {
      btn.addEventListener("click", function () {
        var input = document.getElementById(btn.getAttribute("data-password-toggle"));
        if (!input) return;
        var show = input.type === "password";
        input.type = show ? "text" : "password";
        btn.textContent = show ? "Ẩn" : "Hiện";
      });
    });

    var form = document.getElementById("registerForm");
    if (!form) return;

    form.addEventListener("submit", function (e) {
      var password = form.querySelector("#password");
      var confirm = form.querySelector("#confirmPassword");
      var hint = form.querySelector("[data-confirm-hint]");

      if (password.value !== confirm.value) {
        e.preventDefault();
        confirm.classList.add("is-invalid");
        if (hint) hint.textContent = "Mật khẩu xác nhận không khớp.";
        confirm.focus();
        return;
      }

      confirm.classList.remove("is-invalid");
      if (hint) hint.textContent = "";
    });
  });
})();

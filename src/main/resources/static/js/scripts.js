/*!
* Start Bootstrap - Grayscale v7.0.6 (https://startbootstrap.com/theme/grayscale)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-grayscale/blob/master/LICENSE)
*
* Módosítva: a témát több oldalas alkalmazásban használjuk, ezért a belső oldalakon
* a menüsor mindig "összehúzott" (fehér) állapotban van, a scrollspy-t pedig kivettük.
*/
window.addEventListener('DOMContentLoaded', function () {

    var menu = document.body.querySelector('#mainNav');
    var belsoOldal = document.body.classList.contains('belso');

    var navbarShrink = function () {
        if (!menu) {
            return;
        }
        if (belsoOldal || window.scrollY > 0) {
            menu.classList.add('navbar-shrink');
        } else {
            menu.classList.remove('navbar-shrink');
        }
    };

    navbarShrink();
    document.addEventListener('scroll', navbarShrink);

    // mobilon a menüpontra kattintva csukódjon be a lenyíló menü
    var toggler = document.body.querySelector('.navbar-toggler');
    var linkek = document.querySelectorAll('#navbarResponsive .nav-link');
    for (var i = 0; i < linkek.length; i++) {
        linkek[i].addEventListener('click', function () {
            if (window.getComputedStyle(toggler).display !== 'none') {
                toggler.click();
            }
        });
    }
});

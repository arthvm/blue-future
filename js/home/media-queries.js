const mainDesktop = document.getElementById('main-desktop');
const mainMobile = document.getElementById('main-mobile');
let lastSize = document.documentElement.clientWidth;

function loadDesktop(){
    mainMobile.style.display = "none";
    mainDesktop.style.display = "flex";
}

function loadMobile(){
    mainDesktop.style.display = "none";
    mainMobile.style.display = "flex";
}

window.addEventListener('resize', () => {

    if(document.documentElement.clientWidth >= 768 && lastSize < 768){
        lastSize = document.documentElement.clientWidth;
        loadDesktop();
    }

    if(document.documentElement.clientWidth < 768 && lastSize >= 768){
        lastSize = document.documentElement.clientWidth;
        loadMobile();
    }

})

if(document.documentElement.clientWidth >= 768){
    loadDesktop();
}

if(document.documentElement.clientWidth < 768){
    loadMobile();
}
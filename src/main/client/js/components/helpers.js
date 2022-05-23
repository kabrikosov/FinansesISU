export function getToken(){
    let tmp = document.querySelector("meta[name='_csrf']");
    return tmp && tmp.getAttribute("content");
}
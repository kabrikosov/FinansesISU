export function getToken(){
    let tmp = document.querySelector("meta[name='_csrf']");
    return tmp && tmp.getAttribute("content");
}

export function representDate(date) {
    date = new Date(date);
    date.setMonth(date.getMonth() + 1);
    return date.toLocaleDateString()
}
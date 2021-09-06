export function enToFaNumber(text) {
    const arabicNumbers = ['۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'];
    return String(text).split('').map(c => parseInt(c) ? arabicNumbers[parseInt(c)] : c === '0' ? '۰' : c).join('');
}


export function secondsToMMSS(seconds) {
    if(seconds == null){
        return ".....";
    }
    let dateObj = new Date(seconds * 1000);
    // let hours = dateObj.getUTCHours();
    let minutes = dateObj.getUTCMinutes();
    seconds = dateObj.getSeconds();

    // let timeString = hours.toString().padStart(2, '0') + ':' +
    //     minutes.toString().padStart(2, '0') + ':' +
    //     seconds.toString().padStart(2, '0');

    let timeString = minutes.toString().padStart(2, '0') + ':' +
        seconds.toString().padStart(2, '0');

    return timeString;
}

export const scrollToTop = () => {
    const c = document.documentElement.scrollTop || document.body.scrollTop;
    if (c > 0) {
        window.requestAnimationFrame(scrollToTop);
        window.scrollTo(0, c - c / 8);
    }
};
const changeProgress = () => {
    const progressBarsWidthValues = document.getElementsByClassName("percent-num-value");
    const progressBars = document.getElementsByClassName("progress")

    for (let i = 0; i < progressBarsWidthValues.length; i++) {

        let value = parseInt(progressBarsWidthValues[i].textContent)
        progressBars[i].style.width = `${value}%`;

        if (value <= 50) {
            let num = value * 5;
            progressBars[i].style.background = `rgb(255, ${num}, 0)`;
        } else {
            let num = 255 - ((value - 50) * 5);
            progressBars[i].style.background = `rgb(${num}, 255, 0)`;
        }
    }
};

changeProgress();
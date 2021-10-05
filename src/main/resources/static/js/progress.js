const changeProgress = () => {
    const progressBarsWidthValues = document.getElementsByClassName("percent-value");
    const progressBars = document.getElementsByClassName("progress")

    for (let i = 0; i < progressBarsWidthValues.length; i++) {

        let value = parseInt(progressBarsWidthValues[i].textContent)
        progressBars[i].style.width = `${value}%`;
    }
};

changeProgress();
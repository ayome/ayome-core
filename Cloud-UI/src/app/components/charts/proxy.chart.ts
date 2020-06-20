export let chart = {
    tooltip: {
        enabled: false
    },
    grid: {
        show: false,
        padding: {
            left: 0,
            right: 0
        }
    },
    colors: ["#b84644", "#4576b5"],
    chart: {
        type: "area",
        foreColor: "#929292",
        height: "180px",
        width: "100%",
        toolbar: {
            show: false
        },
        zoom: {
            enabled: false
        }
    },
    dataLabels: {
        enabled: false
    },
    labels: ["CPU", "Memory"],
    yAxis: {
        labels: {
            offsetX: -15
        }
    },
    xAxis: {
        labels: {
            show: false
        }
    },
    series: [
        {
            name: "CPU",
            data: [0, 0, 0, 0, 0, 0, 0, 0]
        },
        {
            name: "Memory",
            data: [0, 0, 0, 0, 0, 0, 0, 0]
        }
    ],
    fill: {
        type: "gradient",
        gradient: {
            shadeIntensity: 1,
            inverseColors: false,
            opacityFrom: 0.5,
            opacityTo: 0,
            stops: [0, 90, 100]
        }
    }
}

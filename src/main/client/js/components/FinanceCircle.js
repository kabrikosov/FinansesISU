import React, {useEffect, useRef, useState} from "react";

const monthNames = {
    en: ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"],
    ru: ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"]
};

export function FinanceCircle() {
    const date = useRef(new Date());
    const statistics = useRef([]);
    const [options, setOptions] = useState({
        'title': '',
        'width': 450,
        'height': 300,
        'backgroundColor': 'transparent',
        chartArea: {left: "3%", top: "3%", width: "100%", height: "100%"}
    });
    const [status, setStatus] = useState(false);

    useEffect(() => {
        initializeChartPie();
    }, [status])

    function previousMonth(ev) {
        date.current.setMonth(date.current.getMonth() - 1)
        document.getElementById("finance-circle__month").textContent = monthNames[lang][date.current.getMonth()] + " " + date.current.getFullYear();
        setStatus(!status);
    }

    function nextMonth(ev){
        date.current.setMonth(date.current.getMonth() + 1)
        document.getElementById("finance-circle__month").textContent = monthNames[lang][date.current.getMonth()] + " " + date.current.getFullYear();
        setStatus(!status);
    }

    function initializeChartPie() {
        let req = window.location.origin + "/api/statistics/sumGroup?start=" +
            new Date(date.current.getFullYear(), date.current.getMonth(), 0).toISOString().substring(0, 10) +
            "&end=" + new Date(date.current.getFullYear(), date.current.getMonth() + 1, 0).toISOString().substring(0, 10);

        fetch(req)
            .then((response) => {
                return response.json();
            })
            .then(response => {
                console.log(response);
                let loaded = response.map(response => [response.category.name, response.sum]);
                loaded.unshift(['Category', 'Money spent']);
                statistics.current = loaded;
                google.charts.load('current', {'packages': ['corechart']});
                google.charts.setOnLoadCallback(ev => {
                    let data = google.visualization.arrayToDataTable(statistics.current);

                    // Display the chart inside the <div> element with id="piechart"
                    let chart = new google.visualization.PieChart(document.getElementById('piechart'));
                    chart.draw(data, options);
                });
            });
    }

    return (
        <div className="finance-circle__wrapper">
            <div className="finance-circle__head">
                <img className="arrow arrow--finance-previous-month" onClick={previousMonth}
                     src="http://localhost:8080/resources/img/arrow.svg"
                     alt="previous month" id="previous-month"/>
                <span className="finance-circle__month" id="finance-circle__month"
                >{monthNames[lang][date.current.getMonth()] + " " + date.current.getFullYear()}</span>
                <img className="arrow arrow--finance-next-month"
                     src="http://localhost:8080/resources/img/arrow.svg"
                     alt="next month" id="next-month" onClick={nextMonth}/>
            </div>
            <div className="finance-circle">
                <div id="piechart"/>
            </div>
        </div>
    )
}
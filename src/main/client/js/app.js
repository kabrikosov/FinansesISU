import ReactDOM from 'react-dom'
import React from 'react'

const monthNames = {
    en: ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"],
    ru: ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"]
};

class App extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <FinanceCircle/>
        )
    }
}

class FinanceCircle extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            date: new Date(),
            statistics: [],
            options: {
                'title': '',
                'width': 450,
                'height': 300,
                'backgroundColor': 'transparent',
                chartArea: {left: "3%", top: "3%", width: "100%", height: "100%"}
            }
        };
    }

    updateDate(date){
        this.setState(prevState => ({
            date: date,
            statistics: prevState.statistics,
            options: prevState.options
        }));
    }

    componentDidMount() {
        this.initializeChartPie();
        this.render();
    }

    render() {
        return (
            <div className="finance-circle__wrapper">
                <div className="finance-circle__head">
                    <img className="arrow arrow--finance-previous-month" onClick={this.previousMonth()}
                         src="http://localhost:8080/resources/img/arrow.svg"
                         alt="previous month" id="previous-month"/>
                    <span className="finance-circle__month" id="finance-circle__month"
                    >{monthNames[lang][this.state.date.getMonth()] + " " + this.state.date.getFullYear()}</span>
                    <img className="arrow arrow--finance-next-month"
                         src="http://localhost:8080/resources/img/arrow.svg"
                         alt="next month" id="next-month" onClick={this.nextMonth()}/>
                </div>
                <div className="finance-circle">
                    <div id="piechart"></div>
                </div>
            </div>
        )
    }

    previousMonth() {
        return (ev) => {
            let date = new Date(this.state.date.getFullYear(), this.state.date.getMonth() - 1, this.state.date.getDay());
            this.updateDate(date)
            document.getElementById("finance-circle__month").textContent = monthNames[lang][this.state.date.getMonth()] + " " + this.state.date.getFullYear();
            this.initializeChartPie(date);
            this.render();
        };
    }

    nextMonth() {
        return (ev) => {
            let date = new Date(this.state.date.getFullYear(), this.state.date.getMonth() + 1, this.state.date.getDay())
            this.updateDate(date)
            document.getElementById("finance-circle__month").textContent = monthNames[lang][this.state.date.getMonth()] + " " + this.state.date.getFullYear();
            this.initializeChartPie(date);
            this.render();
        }
    }

    initializeChartPie(date=null) {
        if (date == null){
            date = this.state.date;
        }
        let requ = window.location.origin + "/api/statistics/sumGroup?start=" +
            new Date(date.getFullYear(), date.getMonth(), 0).toISOString().substring(0, 10) +
            "&end=" + new Date(date.getFullYear(), date.getMonth() + 1, 0).toISOString().substring(0, 10);

        fetch(requ)
            .then((response) => {
                return response.json();
            })
            .then(response => {
                console.log(response);
                let loaded = response.map(response => [response.category.name, response.sum]);
                loaded.unshift(['Category', 'Money spent']);
                this.setState(prevState => ({
                        date: prevState.date,
                        statistics: loaded,
                        options: prevState.options
                    })
                );
                google.charts.load('current', {'packages': ['corechart']});
                google.charts.setOnLoadCallback(ev => {
                    let data = google.visualization.arrayToDataTable(loaded);

                    // Display the chart inside the <div> element with id="piechart"
                    let chart = new google.visualization.PieChart(document.getElementById('piechart'));
                    chart.draw(data, this.state.options);
                });
            });
    }
}

ReactDOM.render(
    <App/>,
    document.getElementById('finance-circle')
)
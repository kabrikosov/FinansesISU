import ReactDOM from 'react-dom'
import React from 'react'
import {Chart} from "react-google-charts";

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
                'width': 400,
                'height': 300,
                'backgroundColor': 'transparent',
                chartArea: {left: 0, top: 0, width: "100%", height: "100%"}
            }
        };
    }

    componentDidMount() {
        var requ = window.location.origin + "/api/statistics/sumGroup?start=" +
            new Date(this.state.date.getFullYear(), this.state.date.getMonth(), 0).toISOString().substring(0, 10) +
            "&end=" + new Date(this.state.date.getFullYear(), this.state.date.getMonth() + 1, 0).toISOString().substring(0, 10);

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
                this.render();
            });
    }

    render() {
        return (
            <div className="finance-circle__wrapper">
                <div className="finance-circle__head">
                    <img className="arrow arrow--finance-previous-month"
                         src="http://localhost:8080/resources/img/arrow.svg"
                         alt="previous month" id="previous-month"/>
                    <span className="finance-circle__month" id="finance-circle__month"
                    >{this.state.date.toISOString().substring(0, 7)}</span>
                    <img className="arrow arrow--finance-next-month"
                         src="http://localhost:8080/resources/img/arrow.svg"
                         alt="next month" id="next-month"/>
                </div>
                <div className="finance-circle">
                    <Chart
                        chartType="PieChart"
                        data={[["Age", "Weight"], [4, 5.5], [8, 12]]}
                        options = {{
                            title: "My Daily Activities",
                            pieHole: 0.4,
                            is3D: false,
                        }}
                        width="100%"
                        height="400px"
                        legendToggle
                    />
                </div>
            </div>
        )
    }
}

ReactDOM.render(
    <App/>,
    document.getElementById('finance-circle')
)
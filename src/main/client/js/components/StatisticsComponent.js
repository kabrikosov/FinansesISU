import React, {useRef, useState, useEffect} from "react";
import {FinanceCircle} from "./FinanceCircle";
import {SubscriptionsComponent} from "./SubscriptionsComponent";

export function StatisticsComponent(){
    return(
        <div className="statistics_wrapper">
            <FinanceCircle />
            <SubscriptionsComponent />
        </div>
    )
}
import React, {useState} from "react";
import {getToken, representDate} from "./helpers";

export function TableRow(props) {
    const [state, setState] = useState({
        showActionGroup: false,
    });

    function update(){
        props.onUpdate();
    }

    function mouseOver(ev){
        setState({
            showActionGroup: true,
        })
    }
    function mouseLeave(ev){
        setState({
            showActionGroup: false,
        })
    }

    return (
        <tr onMouseOver={mouseOver} onMouseLeave={mouseLeave}>
            <td><a href={window.location.origin + `/products/${props.data.id}`}>{props.data?.name}</a></td>
            <td>{props.data?.quantity}x{props.data?.price}</td>
            <td>{props.data?.quantity * props.data?.price}</td>
            <td>{representDate(props.data.date)}</td>
            <ActionGroup id={props.data.id} onDelete={update} show={state.showActionGroup}/>
        </tr>
    )
}

function ActionGroup(props){

    function deleteProduct(e){
        e.preventDefault();
        let req = window.location.origin + '/api/products' + `/${props.id}/detachSubscription`;
        fetch(req, {
            method: 'Get',
            headers: {'X-CSRF-TOKEN': getToken()}
        }).then(resp => {
            if (resp.status === 200)
                props.onDelete()
        }).catch(console.warn);
    }

    return (
        <a onClick={deleteProduct} className={props.show ? "delete-item-button" : "delete-item-button delete-item-button--hidden" }><img src={`${window.location.origin}/resources/img/delete.svg`} /></a>
    )
}
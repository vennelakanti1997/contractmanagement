import React, { Component } from 'react';
import { Dropdown, DropdownButton } from 'react-bootstrap';



export class ActionButton extends Component {

    handleOnCllick = (decision) => {
        console.log("Data: " + this.props.cid + " " + decision)
        let url = 'http://localhost:8080/contract/supplier/createAndModify'
        let requestBody = {
            "id": this.props.cid,
            "status": decision
        }

        try {
            fetch(url, {
                method: 'POST',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${JSON.parse(localStorage.getItem('info')).token}`
                },
                body: JSON.stringify(requestBody)
            }).then((response) => {
                if (response.status === 200) {
                    return response.text()
                }

                else if (response.status === 401) {
                    throw new Error("UnAuthorized Access. Please login again")
                }
                else {
                    throw new Error("Unknown Error Occured. Could Not Create Contract. Please Try Again")
                }
            }

            ).then((text) => {
                console.log(text)
                alert(text)
                //this.props.history.push('/viewcontracts')
                window.location.reload()
            })
                .catch((e) => alert(e))
        } catch (error) {
            console.log(error);
        }
    }
    render() {
        if (this.props.flag === "AcceptReject") {
            return (
                <DropdownButton
                    id="dropdown-basic-button" title="Accept/Reject">
                    <Dropdown.Item onClick={() => this.handleOnCllick("Active - Not Delivered")}>Accept</Dropdown.Item>
                    <Dropdown.Item onClick={() => this.handleOnCllick("Reject")}>Reject</Dropdown.Item>
                </DropdownButton>

            )
        }
        else {
            return (
                <DropdownButton
                    id="dropdown-basic-button" title="Close/Extend">
                    <Dropdown.Item onClick={() => this.handleOnCllick("Close")}>Close</Dropdown.Item>
                    <Dropdown.Item onClick={() => this.handleOnCllick("Active - Not Delivered")}>Extend</Dropdown.Item>
                </DropdownButton>
            )
        }
    }
}
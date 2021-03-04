import React from 'react';
import Button from 'react-bootstrap/Button';
import AdminNavbar from './AdminNavbar';

const DisplayRows = (props) => {
    return <tr>
        <td>{props.cid}</td>
        <td>{props.sid}</td>
        <td>{props.type}</td>
        <td>{props.duration}</td>
        <td >{props.status}</td>
        <td>{props.tnc}</td>
        <td>{props.amenities}</td>
    </tr>;
}

const adminData = JSON.parse(localStorage.getItem('info'))


export class ListActiveContracts extends React.Component {
    constructor(props) {
        super(props);
        this.state = { isLoading: false, data: [], createFlag: false, feedbackFlag: false, emptyData: false }
    }
    componentDidMount() {
        let url = 'http://localhost:8080/contract/contractor/active'
        try {
            fetch(url, {
                headers: {
                    'Authorization': `Bearer ${JSON.parse(localStorage.getItem('info')).token}`

                },

            }).then(response => {
                if (response.status === 200) {
                    return response.json()
                }
                else if (response.status === 400) {
                    throw new Error("Contract with the given Id not found")
                }
                else if (response.status === 401) {
                    throw new Error("UnAuthorized Access. Please login again")
                }
                else {
                    throw new Error("Unknown Error Occured. Could Not Create Contract. Please Try Again")
                }

            }).then((json) => {
                if (json.hasOwnProperty("message")) {
                    this.setState({ emptyData: true })
                    this.setState({ isLoading: true }, () => console.log(this.state.isLoading))
                }
                else {
                    this.setState({ data: json });
                    this.setState({ isLoading: true }, () => console.log(this.state.isLoading))
                }
            }
            )
                .catch((e) => alert("Error: " + e.toString()))
        }
        catch (e) {
            alert("In catch block")
            console.log(e.message)
        }

    }
    render() {
        if (this.state.isLoading === false) {

            return <div>
                <AdminNavbar />
                <div>Data is loading...</div>
            </div>
        }
        else {


            return (

                <div>
                    {adminData.role === "admin" ? (
                        <div>
                            <AdminNavbar />
                            {this.state.emptyData === true ? (
                                <div className="container">
                                    <h1>No Data Available</h1>
                                    <Button variant="primary" href="/createcontract" >Create first Contract</Button>
                                </div>
                            ) :

                                <table className="table container text-center col-8" style={{ "width": "60%" }}>
                                    <thead>
                                        <tr>
                                            <th scope="col">Contract Id</th>
                                            <th scope="col">Supplier Id</th>
                                            <th scope="col">Contract Type</th>
                                            <th scope="col">Duration</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Terms And Conditions</th>
                                            <th scope="col">Amenities</th>
                                        </tr>

                                    </thead>
                                    <tbody>


                                        {this.state.data.map((contract) => <DisplayRows key={contract.id} cid={contract.id} sid={contract.supplierId} type={contract.contractType} duration={contract.duration} tnc={contract.tnc} amenities={contract.amenities} status={contract.status} />)}


                                    </tbody>
                                </table>}
                        </div>
                    ) : (<h1>You are not Authorized</h1>)}

                </div>
            )
        }


    }


}

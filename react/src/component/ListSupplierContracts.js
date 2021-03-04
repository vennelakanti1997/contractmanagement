import React from 'react';

//import { Contract } from './Contract';
import SupplierNavbar from './SupplierNavbar';

const DisplayRows = (props) => {
    return <tr>
        <td>{props.cid}</td>
        <td>{props.type}</td>
        <td>{props.duration}</td>
        <td >{props.status}</td>
        <td>{props.tnc}</td>
        <td>{props.amenities}</td>

    </tr>
}
const supplierData = JSON.parse(localStorage.getItem('info'))

export class ListSupplierContracts extends React.Component {
    constructor(props) {
        super(props);
        this.state = { isLoading: false, data: [], createFlag: false, feedbackFlag: false, emptyData: false }
    }
    componentDidMount() {
        var url = ''
        if (JSON.parse(localStorage.getItem('info')).role === "supplier") {
            url = 'http://localhost:8080/contract/supplier/contracts/' + JSON.parse(localStorage.getItem('info')).id
        }
        else {
            url = 'http://localhost:8080/contract/contractor/contracts'
        }

        const supplierData = JSON.parse(localStorage.getItem('info'))
        try {
            fetch(url, {
                headers: {
                    'Authorization': `Bearer ${supplierData.token}`

                },

            }).then(response => {
                if (response.status === 200) {
                    return response.json()
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
                <SupplierNavbar />
                <div>Data is loading...</div>
            </div>
        }
        else {


            return (

                <div>
                    {supplierData.role === "supplier" ? (
                        <div>
                            <SupplierNavbar />
                            {this.state.emptyData === true ? (
                                <div className="container">
                                    <h1>No Data Available</h1>
                                </div>
                            ) :

                                <table className="table container text-center col-8" style={{ "width": "60%" }}>
                                    <thead>

                                        <tr>
                                            <th scope="col">Contract Type</th>
                                            <th scope="col">Duration</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Terms And Conditions</th>
                                            <th scope="col">Amenities</th>
                                        </tr>

                                    </thead>
                                    <tbody>


                                        {this.state.data.map((contract) => <DisplayRows key={contract.id} cid={contract.id} type={contract.contractType} duration={contract.duration} tnc={contract.tnc} amenities={contract.amenities} status={contract.status} flag={JSON.parse(localStorage.getItem('info')).role} />)}


                                    </tbody>
                                </table>}
                        </div>
                    ) : (<h1>You are not Authorized</h1>)}

                </div>
            )
        }


    }


}

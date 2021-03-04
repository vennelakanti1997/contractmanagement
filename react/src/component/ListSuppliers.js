import React from 'react';
import AdminNavbar from './AdminNavbar';
const DisplayRows = (props) => {
    return <tr>
        <td>{props.sid}</td>
        <td>{props.name}</td>
        <td>{props.type}</td>
        <td>{props.number}</td>
        <td>{props.address}</td>
    </tr>
}

const adminData = JSON.parse(localStorage.getItem('info'))

export class ListSuppliers extends React.Component {
    constructor(props) {
        super(props);
        this.state = { isLoading: false, data: [], emptyData: false }
    }

    componentDidMount() {
        let url = 'http://localhost:8080/contract/contractor/suppliers'
        try {
            fetch(url, {
                headers: {
                    'Authorization': `Bearer ${JSON.parse(localStorage.getItem('info')).token}`
                }

            }).then((response) => {
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
            }

            ).then((json) => {
                if (json.hasOwnProperty("message")) {
                    this.setState({ emptyData: true })
                    this.setState({ isLoading: true }, () => console.log(this.state.isLoading))
                }
                else {
                    this.setState({ data: json });
                    this.setState({ isLoading: true }, () => console.log(this.state.isLoading))
                }
            })
                .catch((e) => alert(e))
        } catch (error) {
            console.log(error);
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
                            {this.state.emptyData === true ?
                                <h1 className="container">No Data Available</h1>
                                // <Button variant="primary" href="/createcontract" >Create first Contract</Button>
                                :

                                <table className="table container text-center col-8" style={{ "width": "60%" }}>
                                    <thead>
                                        <tr>
                                            <th scope="col">Supplier Id</th>
                                            <th scope="col">Supplier Name</th>
                                            <th scope="col">Type</th>
                                            <th scope="col">Contract No.</th>
                                            <th scope="col">Address</th>
                                        </tr>

                                    </thead>
                                    <tbody>
                                        {this.state.data.map((supplier) =>
                                            <DisplayRows key={supplier.id}
                                                sid={supplier.id}
                                                name={supplier.name}
                                                type={supplier.type}
                                                number={supplier.number}
                                                address={supplier.address} />)}
                                    </tbody>
                                </table>}
                        </div>
                    ) : (<h1>You are not Authorized</h1>)}

                </div>
            )
        }
    }
}
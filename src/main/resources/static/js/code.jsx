const user = {
    id : 5,
    age: 33,
    firstName: 'Tom',
    lastName: 'Smit',
    getFullName: function(){
        return `${this.firstName} ${this.lastName}`;
    }
};

class Hello extends React.Component {
    render() {
        return <div>
            <h1>{user.getFullName()}</h1>
            <h1>{user.age}</h1>

        </div>;
    }
}
ReactDOM.render(
    <Hello></Hello>,
    document.getElementById("app")
)
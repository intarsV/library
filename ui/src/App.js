import React,{Component}  from 'react';
import './App.css';
import BookApp from './pages/book/BookApp'

class App extends Component {

  render() {
    return (
        <div className="container">
          <BookApp />
        </div>
    );
  }
}

export default App;

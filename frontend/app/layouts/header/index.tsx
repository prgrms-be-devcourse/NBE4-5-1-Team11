import "./style.css";

const Header = () => {
  return (
    <header className="header">
      <h1>Coffee Management System</h1>
      <nav>
        <ul>
          <li><a href="/">Home</a></li>
          <li><a href="/mypage">My Page</a></li>
          <li><a href="/auth">Auth</a></li>
          <li><a href="/admin">Admin</a></li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;

import { Layout, Button, Space } from 'antd';
import { LogoutOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { ROUTES } from '../../utils/constants';

const { Header: AntHeader } = Layout;

const Header = () => {
  const navigate = useNavigate();
  const { logout, userRole } = useAuth();

  const handleLogout = () => {
    logout();
    navigate(ROUTES.LOGIN);
  };

  return (
    <AntHeader className="bg-white shadow-sm px-6 flex items-center justify-between">
      <h1 className="text-2xl font-bold text-white">CampusFlow</h1>
      <Space>
        <span className="text-white">Role: {userRole}</span>
        <Button
          type="primary"
          danger
          icon={<LogoutOutlined />}
          onClick={handleLogout}
        >
          Logout
        </Button>
      </Space>
    </AntHeader>
  );
};

export default Header;

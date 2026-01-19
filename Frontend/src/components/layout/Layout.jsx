import { Outlet } from 'react-router-dom';
import { Layout as AntLayout } from 'antd';
import Header from './Header';
import Sidebar from './Sidebar';

const { Content } = AntLayout;

const Layout = () => {
  return (
    <AntLayout className="h-screen">
      <Header />
      <AntLayout>
        <Sidebar />
        <AntLayout className="p-6">
          <Content className="bg-gray-100 rounded-lg shadow-sm">
            <Outlet />
          </Content>
        </AntLayout>
      </AntLayout>
    </AntLayout>
  );
};

export default Layout;

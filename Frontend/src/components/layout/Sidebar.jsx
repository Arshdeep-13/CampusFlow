import { Layout, Menu } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import {
  DashboardOutlined,
  UserOutlined,
  TeamOutlined,
  BookOutlined,
  FileTextOutlined,
  LinkOutlined,
} from '@ant-design/icons';
import { useAuth } from '../../hooks/useAuth';
import { ROLES } from '../../utils/constants';

const { Sider } = Layout;

const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { userRole } = useAuth();

  const getMenuItems = () => {
    if (userRole === ROLES.ADMIN) {
      return [
        {
          key: '/admin',
          icon: <DashboardOutlined />,
          label: 'Dashboard',
        },
        {
          key: '/admin/students',
          icon: <UserOutlined />,
          label: 'Students',
        },
        {
          key: '/admin/teachers',
          icon: <TeamOutlined />,
          label: 'Teachers',
        },
        {
          key: '/admin/courses',
          icon: <BookOutlined />,
          label: 'Courses',
        },
        {
          key: '/admin/subjects',
          icon: <FileTextOutlined />,
          label: 'Subjects',
        },
        {
          key: 'assignments',
          icon: <LinkOutlined />,
          label: 'Assignments',
          children: [
            {
              key: '/admin/assign/student-course',
              label: 'Student to Course',
            },
            {
              key: '/admin/assign/teacher-subject',
              label: 'Teacher to Subjects',
            },
            {
              key: '/admin/assign/subjects-course',
              label: 'Subjects to Course',
            },
          ],
        },
      ];
    } else if (userRole === ROLES.TEACHER) {
      return [
        {
          key: '/teacher',
          icon: <DashboardOutlined />,
          label: 'Dashboard',
        },
      ];
    } else if (userRole === ROLES.STUDENT) {
      return [
        {
          key: '/student',
          icon: <DashboardOutlined />,
          label: 'Dashboard',
        },
      ];
    }
    return [];
  };

  return (
    <Sider width={300} className="bg-white shadow-sm">
      <Menu
        mode="inline"
        selectedKeys={[location.pathname]}
        items={getMenuItems()}
        onClick={({ key }) => navigate(key)}
        className="h-full"
        style={{ paddingTop: '1rem', gap: '1rem' }}
      />
    </Sider>
  );
};

export default Sidebar;

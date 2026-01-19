import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Form, Input, Button, Card, message, Typography } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { authService } from '../../services/authService';
import { useAuth } from '../../hooks/useAuth';
import { ROUTES, ROLES } from '../../utils/constants';

const { Title } = Typography;

const LoginPage = () => {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { login } = useAuth();

  const onFinish = async (values) => {
    setLoading(true);
    try {
      const response = await login(values);
      
      message.success('Login successful!');
      
      // Get role from token to navigate to correct route
      const { getRoleFromToken } = await import('../../utils/jwt');
      const token = authService.getToken();
      const role = token ? getRoleFromToken(token) : null;
      
      // Navigate based on role
      setTimeout(() => {
        if (role === ROLES.ADMIN) {
          navigate(ROUTES.ADMIN);
        } else if (role === ROLES.TEACHER) {
          navigate(ROUTES.TEACHER);
        } else if (role === ROLES.STUDENT) {
          navigate(ROUTES.STUDENT);
        } else {
          navigate(ROUTES.LOGIN);
        }
      }, 500);
    } catch (error) {
      message.error(error.response?.data?.message || 'Login failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 to-indigo-100">
      <Card className="w-full max-w-md shadow-lg">
        <div className="text-center mb-6">
          <Title level={2} className="mb-2">CampusFlow</Title>
          <p className="text-gray-600">Login to your account</p>
        </div>
        
        <Form
          name="login"
          onFinish={onFinish}
          layout="vertical"
          size="large"
        >
          <Form.Item
            name="username"
            rules={[{ required: true, message: 'Please input your username!' }]}
          >
            <Input
              prefix={<UserOutlined />}
              placeholder="Username"
            />
          </Form.Item>

          <Form.Item
            name="password"
            rules={[{ required: true, message: 'Please input your password!' }]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="Password"
            />
          </Form.Item>

          <Form.Item>
            <Button
              type="primary"
              htmlType="submit"
              className="w-full"
              loading={loading}
            >
              Login
            </Button>
          </Form.Item>
        </Form>

        <div className="text-center mt-4">
          <p className="text-gray-600">
            Don't have an account?{' '}
            <Button type="link" onClick={() => navigate('/signin')} className="p-0">
              Sign Up
            </Button>
          </p>
        </div>
      </Card>
    </div>
  );
};

export default LoginPage;

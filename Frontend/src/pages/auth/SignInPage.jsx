import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Form, Input, Button, Card, message, Typography } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { authService } from '../../services/authService';
import { ROUTES } from '../../utils/constants';

const { Title } = Typography;

const SignInPage = () => {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values) => {
    setLoading(true);
    try {
      const response = await authService.signIn({
        username: values.username,
        password: values.password,
      });
      
      if (response.success) {
        message.success('Account created successfully! Please login.');
        navigate(ROUTES.LOGIN);
      } else {
        message.error(response.message || 'Sign up failed. Please try again.');
      }
    } catch (error) {
      message.error(error.response?.data?.message || 'Sign up failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 to-indigo-100">
      <Card className="w-full max-w-md shadow-lg">
        <div className="text-center mb-6">
          <Title level={2} className="mb-2">CampusFlow</Title>
          <p className="text-gray-600">Create a new student account</p>
        </div>
        
        <Form
          name="signin"
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
              Sign Up
            </Button>
          </Form.Item>
        </Form>

        <div className="text-center mt-4">
          <p className="text-gray-600">
            Already have an account?{' '}
            <Button type="link" onClick={() => navigate(ROUTES.LOGIN)} className="p-0">
              Login
            </Button>
          </p>
        </div>
      </Card>
    </div>
  );
};

export default SignInPage;

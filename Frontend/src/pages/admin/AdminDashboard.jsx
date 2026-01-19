import { Card, Row, Col, Statistic, Button, Modal, Form, Input, message, Space } from 'antd';
import { UserOutlined, TeamOutlined, BookOutlined, FileTextOutlined, PlusOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { adminService } from '../../services/adminService';
import { useEffect, useState } from 'react';

const AdminDashboard = () => {
  const [studentsCount, setStudentsCount] = useState(0);
  const [teachersCount, setTeachersCount] = useState(0);
  const [coursesCount, setCoursesCount] = useState(0);
  const [subjectsCount, setSubjectsCount] = useState(0);
  const [isAdminModalVisible, setIsAdminModalVisible] = useState(false);
  const [adminForm] = Form.useForm();

  useEffect(() => {
    const fetchData = async () => {
      let [studentsCountResponse, teachersCountResponse, coursesCountResponse, subjectsCountResponse] = await Promise.all([
        adminService.getStudentsCount(),
        adminService.getTeachersCount(),
        adminService.getCoursesCount(),
        adminService.getSubjectsCount(),
      ]);
      
      setStudentsCount(studentsCountResponse?.totalStudents || 0);
      setTeachersCount(teachersCountResponse?.teacherCount || 0);
      setCoursesCount(coursesCountResponse?.courseCount || 0);
      setSubjectsCount(subjectsCountResponse?.subjectCount || 0);
    };
    fetchData();
  }, []);

  const handleCreateAdmin = () => {
    adminForm.resetFields();
    setIsAdminModalVisible(true);
  };

  const handleAdminSubmit = async (values) => {
    try {
      const response = await adminService.createAdmin(values);
      if (response.success) {
        message.success('Admin created successfully');
        setIsAdminModalVisible(false);
        adminForm.resetFields();
      } else {
        message.error(response.message || 'Failed to create admin');
      }
    } catch (error) {
      message.error(error.response?.data?.message || 'Failed to create admin');
    }
  };

  return (
    <div className="h-full flex flex-col justify-evenly" style={{ padding: '1rem' }}>
      <h1 className="text-3xl font-bold mb-6 text-center">Admin Dashboard</h1>
      
      <Row gutter={[16, 16]} className="mb-6">
        <Col xs={24} sm={12} lg={6}>
          <Link to="/admin/students">
            <Card>
              <Statistic
                title="Students"
                value={studentsCount}
                prefix={<UserOutlined />}
                valueStyle={{ color: '#3f8600' }}
              />
            </Card>
          </Link>
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <Link to="/admin/teachers">
            <Card>
              <Statistic
                title="Teachers"
                value={teachersCount}
                prefix={<TeamOutlined />}
                valueStyle={{ color: '#1890ff' }}
              />
            </Card>
          </Link>
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <Link to="/admin/courses">
            <Card>
              <Statistic
                title="Courses"
                value={coursesCount}
                prefix={<BookOutlined />}
                valueStyle={{ color: '#722ed1' }}
              />
            </Card>
          </Link>
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <Link to="/admin/subjects">
            <Card>
              <Statistic
                title="Subjects"
                value={subjectsCount}
                prefix={<FileTextOutlined />}
                valueStyle={{ color: '#eb2f96' }}
              />
            </Card>
          </Link>
        </Col>
      </Row>

      <Row gutter={[16, 16]}>
        <Col xs={24}>
          <Card title="Quick Actions" className="h-full text-center">
            <div className="flex gap-4 justify-center flex-wrap">
              <Button 
                type="primary" 
                icon={<PlusOutlined />}
                onClick={handleCreateAdmin}
                size="large"
              >
                Create Admin
              </Button>
              <Link to="/admin/assign/student-course">
                <Button type="default" block className="text-left">
                  Assign Student to Course
                </Button>
              </Link>
              <Link to="/admin/assign/teacher-subject">
                <Button type="default" block className="text-left">
                  Assign Teacher to Subjects
                </Button>
              </Link>
              <Link to="/admin/assign/subjects-course">
                <Button type="default" block className="text-left">
                  Assign Subjects to Course
                </Button>
              </Link>
            </div>
          </Card>
        </Col>
      </Row>

      <Modal
        title="Create Admin"
        open={isAdminModalVisible}
        onCancel={() => {
          setIsAdminModalVisible(false);
          adminForm.resetFields();
        }}
        footer={null}
        width={500}
      >
        <Form
          form={adminForm}
          layout="vertical"
          onFinish={handleAdminSubmit}
        >
          <Form.Item
            name="username"
            label="Username"
            rules={[{ required: true, message: 'Please enter username' }]}
          >
            <Input placeholder="Username" size="large" />
          </Form.Item>

          <Form.Item
            name="password"
            label="Password"
            rules={[{ required: true, message: 'Please enter password' }]}
          >
            <Input.Password placeholder="Password" size="large" />
          </Form.Item>

          <Form.Item className="mb-0">
            <Space className="w-full justify-end">
              <Button onClick={() => setIsAdminModalVisible(false)} size="large">
                Cancel
              </Button>
              <Button type="primary" htmlType="submit" size="large">
                Create
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default AdminDashboard;

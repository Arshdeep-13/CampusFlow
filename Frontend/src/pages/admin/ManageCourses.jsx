import { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Input, message, Space, Popconfirm, Card, Row, Col } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { adminService } from '../../services/adminService';

const ManageCourses = () => {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingCourse, setEditingCourse] = useState(null);
  const [form] = Form.useForm();

  const fetchCourses = async () => {
    setLoading(true);
    try {
      const response = await adminService.getCourses();
      setCourses(response);
    } catch (err) {
      message.error('Failed to fetch courses');
      console.error('Error fetching courses:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCourses();
  }, []);

  const handleCreate = () => {
    setEditingCourse(null);
    form.resetFields();
    setIsModalVisible(true);
  };

  const handleEdit = (record) => {
    setEditingCourse(record);
    form.setFieldsValue({
      courseName: record.name,
      courseCode: record.courseCode,
    });
    setIsModalVisible(true);
  };

  const handleDelete = async (courseId) => {
    try {
      await adminService.deleteCourse(courseId);
      message.success('Course deleted successfully');
      fetchCourses();
    } catch (error) {
      message.error(error.response?.data?.message || 'Failed to delete course');
    }
  };

  const handleSubmit = async (values) => {
    try {
      if (editingCourse) {
        await adminService.editCourse(editingCourse.id, values);
        message.success('Course updated successfully');
      } else {
        await adminService.createCourse(values);
        message.success('Course created successfully');
      }
      setIsModalVisible(false);
      form.resetFields();
      fetchCourses();
    } catch (error) {
      message.error(error.response?.data?.message || 'Operation failed');
    }
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: '15%',
      align: 'center',
    },
    {
      title: 'Course Name',
      dataIndex: 'name',
      key: 'name',
      width: '35%',
      align: 'center',
    },
    {
      title: 'Course Code',
      dataIndex: 'courseCode',
      key: 'courseCode',
      width: '25%',
      align: 'center',
    },
    {
      title: 'Actions',
      key: 'actions',
      width: '25%',
      align: 'center',
      render: (_, record) => (
        <Space>
          <Button
            type="primary"
            icon={<EditOutlined />}
            onClick={() => handleEdit(record)}
            size="small"
          >
            Edit
          </Button>
          <Popconfirm
            title="Are you sure you want to delete this course?"
            onConfirm={() => handleDelete(record.id)}
            okText="Yes"
            cancelText="No"
          >
            <Button danger icon={<DeleteOutlined />} size="small">
              Delete
            </Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <div className="h-full flex flex-col" style={{ padding: '1rem' }}>
      <div className="flex justify-between items-center" style={{ marginBottom: '1rem' }}>
        <h1 className="text-2xl font-bold">Manage Courses</h1>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={handleCreate}
          size="large"
        >
          Add Course
        </Button>
      </div>

      <Card>
        <Table
          columns={columns}
          dataSource={courses}
          loading={loading}
          rowKey="id"
          scroll={{ x: 'max-content' }}
        />
      </Card>

      <Modal
        title={editingCourse ? 'Edit Course' : 'Create Course'}
        open={isModalVisible}
        onCancel={() => {
          setIsModalVisible(false);
          form.resetFields();
        }}
        footer={null}
        width={500}
      >
        <Form
          form={form}
          layout="vertical"
          onFinish={handleSubmit}
        >
          <Form.Item
            name="courseName"
            label="Course Name"
            rules={[{ required: true, message: 'Please enter course name' }]}
          >
            <Input placeholder="Course Name" size="large" />
          </Form.Item>

          <Form.Item
            name="courseCode"
            label="Course Code"
            rules={[{ required: true, message: 'Please enter course code' }]}
          >
            <Input placeholder="Course Code" size="large" />
          </Form.Item>

          <Form.Item className="mb-0">
            <Space className="w-full justify-end">
              <Button onClick={() => setIsModalVisible(false)} size="large">
                Cancel
              </Button>
              <Button type="primary" htmlType="submit" size="large">
                {editingCourse ? 'Update' : 'Create'}
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ManageCourses;

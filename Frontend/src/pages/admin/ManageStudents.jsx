import { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Input, message, Space, Popconfirm, Card, Row, Col } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { adminService } from '../../services/adminService';

const ManageStudents = () => {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingStudent, setEditingStudent] = useState(null);
  const [form] = Form.useForm();


  const fetchStudents = async () => {
    setLoading(true);
    try {
      const response = await adminService.getStudents();
      setStudents(response);
    } catch (err) {
      message.error('Failed to fetch students');
      console.error('Error fetching students:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchStudents();
  }, []);

  const handleCreate = () => {
    setEditingStudent(null);
    form.resetFields();
    setIsModalVisible(true);
  };

  const handleEdit = (record) => {
    setEditingStudent(record);
    form.setFieldsValue({
      name: record.name,
    });
    setIsModalVisible(true);
  };

  const handleDelete = async (studentId) => {
    try {
      await adminService.deleteStudent(studentId);
      message.success('Student deleted successfully');
      fetchStudents();
    } catch (error) {
      message.error(error.response?.data?.message || 'Failed to delete student');
    }
  };

  const handleSubmit = async (values) => {
    try {
      if (editingStudent) {
        await adminService.editStudent({
          id: editingStudent.id,
          ...values,
        });
        message.success('Student updated successfully');
      } else {
        await adminService.createStudent(values);
        message.success('Student created successfully');
      }
      setIsModalVisible(false);
      form.resetFields();
      fetchStudents();
    } catch (error) {
      message.error(error.response?.data?.message || 'Operation failed');
    }
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: '10%',
      align: 'center',
    },
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
      width: '30%',
      align: 'center',
    },
    {
      title: 'Course',
      dataIndex: ['course', 'name'],
      key: 'course',
      width: '10%',
      align: 'center',
    },
    {
      title: 'Actions',
      key: 'actions',
      width: '30%',
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
            title="Are you sure you want to delete this student?"
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
        <h1 className="text-2xl font-bold">Manage Students</h1>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={handleCreate}
          size="large"
        >
          Add Student
        </Button>
      </div>

      <Card>
        <Table
          columns={columns}
          dataSource={students}
          loading={loading}
          rowKey="id"
          scroll={{ x: 'max-content' }}
        />
      </Card>

      <Modal
        title={editingStudent ? 'Edit Student' : 'Create Student'}
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
            name="name"
            label="Name"
            rules={[{ required: true, message: 'Please enter student name' }]}
          >
            <Input placeholder="Student Name" size="large" />
          </Form.Item>

          {!editingStudent && (
            <Form.Item
              name="password"
              label="Password"
              rules={[{ required: true, message: 'Please enter password' }]}
            >
              <Input.Password placeholder="Password" size="large" />
            </Form.Item>
          )}

          <Form.Item className="mb-0">
            <Space className="w-full justify-end">
              <Button onClick={() => setIsModalVisible(false)} size="large">
                Cancel
              </Button>
              <Button type="primary" htmlType="submit" size="large">
                {editingStudent ? 'Update' : 'Create'}
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ManageStudents;
